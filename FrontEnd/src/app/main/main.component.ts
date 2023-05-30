import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {RegistrationService} from "../registration.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserFull} from "../userFull.model";
import {MatDialog} from "@angular/material/dialog";
import {AdItemModalComponent} from "../ad-item-modal/ad-item-modal.component";
import {ItemFull} from "../itemFull.model";
import {ItemService} from "../item.service";
import {EditItemModalComponent} from "../edit-item-modal/edit-item-modal.component";
import {CartItem} from "../cart.model";
import {Router} from "@angular/router";


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });
  cart: CartItem[] = [];
  searchTerm: string = '';
  menuButton: any;
  cartButton: any;
  user: UserFull | null;
  items: ItemFull[] = [];
  totalPrice: number = 0;
  isCheckout: boolean = false;
  constructor(private registrationService: RegistrationService, private snackBar : MatSnackBar, private dialog: MatDialog, private itemService: ItemService, private router: Router) {}
  ngOnInit() {
    this.getAllItems();
  }
  onLoginFormSubmit() {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email || '';
      const password = this.loginForm.value.password || '';
      this.registrationService.login(email, password).subscribe(
        {
          next: (response: UserFull) => {
           this.user = response;
           console.log(this.user);
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
  logout() {
    this.user = null;
  }

  openModal() {
    const dialogRef = this.dialog.open(AdItemModalComponent, {
      width: '50%',
    });
    dialogRef.afterClosed().subscribe((result: ItemFull) => {
      this.getAllItems();
    });
  }

  clearSearch() {
    this.searchTerm = '';
    this.filterItems();
  }

  public getAllItems() {
    this.itemService.getAllItems().subscribe(
      {
        next: (items: ItemFull[]) => {
          this.items = items;
        },
        error: (error: any) => {
          this.snackBar.open(error.error.message, 'OK', {
            duration: 3000
          });

        }
      }
    );
  }

   filterItems() {
    if (this.searchTerm === '') {
           this.getAllItems();
       } else {
           this.itemService.filterItems(this.searchTerm).subscribe(
               {
                   next: (items: ItemFull[]) => {
                       this.items = items;
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



  removeFromCart(index: number) {
    this.cart.splice(index, 1);
    this.calculateTotalPrice();
  }

  updateCartItem(cartItem: CartItem) {
    cartItem.totalPrice = cartItem.item.price * cartItem.quantity;
    this.calculateTotalPrice();
  }

  calculateTotalPrice() {
    this.totalPrice = this.cart.reduce((total, cartItem) => total + cartItem.totalPrice, 0);
  }

  Checkout() {
    this.isCheckout = true;
  }

  protected readonly event = event;
}
