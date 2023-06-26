import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ItemService} from "../item.service";
import {UserFull} from "../models/userFull.model";
import {Item} from "../models/item.model";

@Component({
  selector: 'app-ad-item-modal',
  templateUrl: './ad-item-modal.component.html',
  styleUrls: ['./ad-item-modal.component.css']
})
export class AdItemModalComponent {
  itemForm: FormGroup;

  constructor(
    private itemService: ItemService,
    public dialogRef: MatDialogRef<AdItemModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.itemForm = new FormGroup({
      name: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      image: new FormControl('', Validators.required),
      category: new FormControl('', Validators.required)
    });
  }

  closeModal(): void {
    this.dialogRef.close();
  }

  saveItem(): void {
    if (this.itemForm.valid) {
      const item : Item = {
        name: this.itemForm.value.name,
        price: this.itemForm.value.price,
        description: this.itemForm.value.description,
        imageUrl: this.itemForm.value.image,
        category: this.itemForm.value.category,
      }
      const formData = this.itemForm.value;
      this.itemService.addItem(item).subscribe(
        {
          next: (response) => {
            console.log(response);
          },
          error: (error: any) => {
            console.log(error);
          }
        }
      );
      this.dialogRef.close(formData);
    }
  }
}
