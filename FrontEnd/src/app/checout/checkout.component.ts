import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemFull } from "../models/itemFull.model";
import { CartItem } from "../models/cart.model";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Order } from "../models/order.model";
import { ItemService } from "../item.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-checout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit{
  @Input() cart: CartItem[] = [];
  @Input() totalPrice: number;
  @Input() shoppingPoints: number | undefined;
  @Output() cancelCheckoutRequested = new EventEmitter<boolean>();

  checkoutForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    city: new FormControl('', [Validators.required]),
    zipCode: new FormControl('', [Validators.required])
  });

  ngOnInit() {
    const SavedEmail = localStorage.getItem('email');
    if (SavedEmail) {
        this.checkoutForm.get('email')?.setValue(SavedEmail);
    }
  }

  constructor(private itemService: ItemService, private snackBar:  MatSnackBar) { }

  CancelCheckout() {
    this.cancelCheckoutRequested.emit(false);
  }

  ConfirmCheckout() {
    const order: Order = {
      firstName: this.checkoutForm.get('firstName')?.value || "",
      lastName: this.checkoutForm.get('lastName')?.value || "",
      email: this.checkoutForm.get('email')?.value || "",
      address: this.checkoutForm.get('address')?.value || "",
      city: this.checkoutForm.get('city')?.value || "",
      zipCode: this.checkoutForm.get('zipCode')?.value  || "",
      items: this.cart
    };

    this.itemService.placeOrder(order).subscribe({
      next:(response: Order) => {
        this.snackBar.open('Congratulations! Your order has been successfully placed with Aperture Science. We thank you for choosing our innovative products and services', 'Close', {
          duration: 10000,
        });
        this.checkoutForm.reset();
        this.cart = [];
        this.totalPrice = 0;
        localStorage.removeItem('cart');
        this.CancelCheckout();
      },
      error: (error) => {
        console.log(order);
        console.error('Error saving order:', error);
      }
    });
  }
}
