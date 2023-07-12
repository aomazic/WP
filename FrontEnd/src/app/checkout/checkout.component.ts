import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemFull } from "../models/itemFull.model";
import { CartItem } from "../models/cart.model";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Order } from "../models/order.model";
import { ItemService } from "../item.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit{
  @Input() cart: CartItem[] = [];
  @Input() totalPrice: number;
  @Input() shoppingPoints: number | undefined;
  @Output() cancelCheckoutRequested = new EventEmitter<boolean>();
  @Output() checkoutCompleted = new EventEmitter<void>();
  isPromoCodeApplied: boolean = false;
  discount: number = 0;
  discountedPrice: number = 0;

  checkoutForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    city: new FormControl('', [Validators.required]),
    zipCode: new FormControl('', [Validators.required]),
    promoCode: new FormControl('')
  });

  ngOnInit() {
    const SavedEmail = localStorage.getItem('email');
    if (SavedEmail) {
        this.checkoutForm.get('email')?.setValue(SavedEmail);
    }
    const SavedDiscount = localStorage.getItem('discount');
    if (SavedDiscount) {
      this.discount = parseInt(SavedDiscount);
      this.checkoutForm.get('promoCode')?.setValue(SavedDiscount);
      this.isPromoCodeApplied = true;
      this.checkoutForm.get('promoCode')?.disable();
      this.discountedPrice = this.totalPrice * (100 - this.discount)/100;
    }
    else {
        this.discountedPrice = this.totalPrice;
    }
    const promoCode = localStorage.getItem('promoCode');
    if (promoCode) {
      this.checkoutForm.get('promoCode')?.setValue(promoCode);
    }
  }

  constructor(private itemService: ItemService, private snackBar:  MatSnackBar) { }

  CancelCheckout() {
    this.cancelCheckoutRequested.emit(false);
  }
  UsePromoCode() {
    const promoCode = this.checkoutForm.get('promoCode')?.value || "";
    this.itemService.usePromoCode(promoCode).subscribe({
      next: (response: string) => {
        this.discount = parseInt(response);
        localStorage.setItem('discount', JSON.stringify(this.discount));
        localStorage.setItem('promoCode', JSON.stringify(promoCode));
        this.totalPrice -= this.discount;
        this.isPromoCodeApplied = true;
        this.checkoutForm.get('promoCode')?.disable();
        this.discountedPrice = this.totalPrice * (100 - this.discount)/100;
      },
      error: (error) => {
        console.log(error);
        this.snackBar.open(error.error.message, 'Close', {
          duration: 10000,
        });
      }
    });
  }

  ConfirmCheckout() {
    const order: Order = {
      firstName: this.checkoutForm.get('firstName')?.value || "",
      lastName: this.checkoutForm.get('lastName')?.value || "",
      email: this.checkoutForm.get('email')?.value || "",
      address: this.checkoutForm.get('address')?.value || "",
      city: this.checkoutForm.get('city')?.value || "",
      zipCode: this.checkoutForm.get('zipCode')?.value  || "",
      items: this.cart,
      totalPrice: this.discountedPrice,
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
        this.ResetPromoCode();
        this.CancelCheckout();
        this.checkoutCompleted.emit();
      },
      error: () => {
       this.snackBar.open('Error placing order', 'Close', {
         duration: 10000,
         });
       }
      });
  }
  ResetPromoCode() {
    this.discount = 0;
    localStorage.removeItem('discount');
    localStorage.removeItem('promoCode');
    this.isPromoCodeApplied = false;
    this.checkoutForm.get('promoCode')?.enable();
  }
}
