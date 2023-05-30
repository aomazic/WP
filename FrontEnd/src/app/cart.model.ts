import {ItemFull} from "./itemFull.model";

export interface CartItem {
    item: ItemFull;
    quantity: number;
    totalPrice: number;
}

