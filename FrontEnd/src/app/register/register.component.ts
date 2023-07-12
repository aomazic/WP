import { Component } from '@angular/core';
import {User} from "../models/user.model";
import {RegistrationService} from "../registration.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})


export class RegisterComponent {

  passwordMismatch : Boolean = false;

  registerForm = new FormGroup({
    username: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required),
    terms: new FormControl('', Validators.required)
  });

  constructor(private registrationService: RegistrationService, private snackBar:  MatSnackBar, private router: Router) { }

  onRegister(): void {
    if(this.registerForm.get('password')?.value != this.registerForm.get('confirmPassword')?.value){
        this.passwordMismatch = true;
        this.registerForm.get('confirmPassword')?.setValue('');
        this.registerForm.get('password')?.setValue('');
        return;
    }
    else this.passwordMismatch = false;

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
          const email = this.registerForm.get('email')?.value;
          this.router.navigate(['/confirm-email'], { queryParams: { email: email } }).then(() => {
          })
            .catch((error) => {
              console.log('Error occurred during navigation:', error);
            });
          }
      },
      error: (error) => {
        console.log(error);
      }

    });
  }

  cancel(): void {
    this.router.navigate(['/']).then(() => {
      })
          .catch((error) => {
              console.log('Error occurred during navigation:', error);
          });
  }

}
