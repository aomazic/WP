import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { MatButtonModule } from '@angular/material/button';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {NgOptimizedImage} from "@angular/common";
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ConfirmEmailComponent } from './confirm-email/confirm-email.component';
import { AppRoutingModule } from './app-routing.module';
import { MainComponent } from './main/main.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import { AdItemModalComponent } from './ad-item-modal/ad-item-modal.component';
import {MatDialogModule} from '@angular/material/dialog';
import { EditItemModalComponent } from './edit-item-modal/edit-item-modal.component';
import {MatTabsModule} from '@angular/material/tabs';
import { ItemListComponent } from './item-list/item-list.component';
import { CheckoutComponent } from './checout/checkout.component';
@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    ConfirmEmailComponent,
    MainComponent,
    AdItemModalComponent,
    EditItemModalComponent,
    ItemListComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    NgOptimizedImage,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    AppRoutingModule,
    MatMenuModule,
    MatIconModule,
    MatDialogModule,
    MatTabsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
