import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ItemFull } from "../models/itemFull.model";
import { CartItem } from "../models/cart.model";
import { EditItemModalComponent } from "../edit-item-modal/edit-item-modal.component";
import { MatDialog } from "@angular/material/dialog";
import { ItemService } from "../item.service";
import { MatSnackBar } from "@angular/material/snack-bar";

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent {

  @Input() items: ItemFull[] = [];
  @Input() user: any;
  @Input() cart: any[];
  @Input() totalPrice: number;
  @Output() getAllItemsRequested = new EventEmitter();
  @Output() totalPriceChanged = new EventEmitter<number>(); // Event to notify the change in totalPrice

  constructor(private dialog: MatDialog, private itemService: ItemService, private snackBar: MatSnackBar) { }

  filterItemsByCategory(category: string): ItemFull[] {
    if (category === 'all') {
      return this.items;
    } else {
      return this.items.filter(item => item.category === category);
    }
  }

  getAllItems() {
    this.getAllItemsRequested.emit();
  }

  addToCart(item: ItemFull) {
    this.snackBar.open("Item added to cart", 'OK', {
      duration: 3000
    });
    const existingCartItem = this.cart.find(cartItem => cartItem.item.id === item.id);
    if (existingCartItem) {
      existingCartItem.quantity++;
      existingCartItem.totalPrice = existingCartItem.item.price * existingCartItem.quantity;
      this.totalPrice += item.price;
    } else {
      const newCartItem: CartItem = {
        item: item,
        quantity: 1,
        totalPrice: item.price,
      };
      this.cart.push(newCartItem);
      localStorage.setItem('cart', JSON.stringify(this.cart));
      this.totalPrice += item.price;
    }
    this.totalPriceChanged.emit(this.totalPrice);
  }

  openEditModal(item: ItemFull) {
    const dialogRef = this.dialog.open(EditItemModalComponent, {
      width: '50%',
      data: item,
    });
    dialogRef.afterClosed().subscribe((result: ItemFull) => {
      this.getAllItems();
    });
  }

  DeleteItem(id: number) {
    this.itemService.deleteItemById(id).subscribe(
      {
        next: () => {
          this.getAllItems();
        },
        error: (error: any) => {
          this.snackBar.open(error.error.message, 'OK', {
            duration: 3000
          });
        }
      }
    );
  }

}
