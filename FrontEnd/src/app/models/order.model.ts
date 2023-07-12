import {Item} from "./item.model";
import {CartItem} from "./cart.model";

export interface Order {
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  city: string;
  zipCode: string;
  items: CartItem[];
  totalPrice: number;
}
