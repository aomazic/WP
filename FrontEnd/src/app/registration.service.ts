
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {User} from "./user.model";
import {HttpClient, HttpParams} from "@angular/common/http";

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
  login(email: string, password: string): Observable<any> {
    const url = `${this.baseUrl}/login?email=${email}&password=${password}`;
    return this.http.get<any>(url);
  }
  resendEmail(email: string): Observable<string> {
    const params = new HttpParams().set('email', email);
    return this.http.get(`${this.baseUrl}/resend`, { params, responseType: 'text' });
  }

}
