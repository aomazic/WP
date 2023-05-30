import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { RegistrationService } from '../registration.service';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.css']
})
export class ConfirmEmailComponent {
  email: string;

  constructor(private route: ActivatedRoute, private registrationService: RegistrationService, private snackBar : MatSnackBar) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
    });
  }

  resendEmail() {
    this.registrationService.resendEmail(this.email).subscribe(
      {
        next: () => {
          this.snackBar.open('Email sent', 'OK', {
                duration: 3000,
            });
        }
        , error: () => {
              this.snackBar.open('Email not sent', 'OK', {
                  duration: 3000,
              });
        }
      });
  }
}
