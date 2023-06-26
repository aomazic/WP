import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Item} from "./models/item.model";
import {ItemFull} from "./models/itemFull.model";
import {Order} from "./models/order.model";

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private baseUrl = 'http://localhost:8080/api/items';

  constructor(private http: HttpClient) { }

  getAllItems(): Observable<ItemFull[]> {
    return this.http.get<ItemFull[]>(this.baseUrl);
  }

  getItemById(id: number): Observable<ItemFull> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ItemFull>(url);
  }

  addItem(item: Item): Observable<ItemFull> {
    return this.http.post<ItemFull>(this.baseUrl, item);
  }

  deleteItemById(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  updateItem(id: number, item: Item): Observable<ItemFull> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<ItemFull>(url, item);
  }

  filterItems(searchTerm: string): Observable<ItemFull[]> {
    const params = new HttpParams().set('searchTerm', searchTerm);
    return this.http.get<ItemFull[]>(`${this.baseUrl}/filter`, { params });
  }
  placeOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}/order`, order);
  }

}
