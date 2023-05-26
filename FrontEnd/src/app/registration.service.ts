
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {User} from "./user.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private baseUrl = 'http://localhost:8080/api/registration';

  constructor(private http: HttpClient) { }
  register(user: User): Observable<string> {
    const url = `${this.baseUrl}/register`;
    return this.http.post(url, user, { responseType: 'text' });
  }
  login(email: string, password: string): Observable<User> {
    const url = `${this.baseUrl}/login?email=${email}&password=${password}`;
    return this.http.get<User>(url);
  }
}
