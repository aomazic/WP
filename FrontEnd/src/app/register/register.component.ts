import { Component } from '@angular/core';
import {User} from "../user.model";
import {RegistrationService} from "../registration.service";
import {FormControl, FormGroup} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl('')
  });

  constructor(private registrationService: RegistrationService, private snackBar:  MatSnackBar) { }

  onRegister(): void {
    const user: User = {
      username: this.registerForm.get('username')?.value || '',
      password: this.registerForm.get('password')?.value || '',
      email: this.registerForm.get('email')?.value || ''
    };

    this.registrationService.register(user).subscribe({
      next: (response:string) => {
        if (response === 'User already exists') {
          this.registerForm.get('email')?.setValue('');
          this.snackBar.open('Your are already registered please click login instead', 'Close', {
                duration: 5000,
            });
        }
        else {

          }
      },
      error: (error) => {
        console.log(error);
        if (error.error === 'email already taken') {
          this.snackBar.open('Email already taken', 'Close', {
            duration: 3000,
          });
        }
      },
      complete: () => {
        console.log('Registration successful!');
      }

    });
  }
}
