import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ItemFull} from "../itemFull.model";
import {CartItem} from "../cart.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-checout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent{
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
  CancelCheckout() {
    this.cancelCheckoutRequested.emit(false);
  }

  ConfirmCheckout() {

  }
}
