import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemService} from "../item.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Item} from "../item.model";

@Component({
  selector: 'app-edit-item-modal',
  templateUrl: './edit-item-modal.component.html',
  styleUrls: ['./edit-item-modal.component.css']
})
export class EditItemModalComponent {
  itemForm: FormGroup;
  id : number;
  constructor(
    private itemService: ItemService,
    public dialogRef: MatDialogRef<EditItemModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.itemForm = new FormGroup({
      name: new FormControl(this.data.name, Validators.required),
      price: new FormControl(this.data.price, Validators.required),
      description: new FormControl(this.data.description, Validators.required),
      imageUrl: new FormControl(this.data.imageUrl, Validators.required),
      category: new FormControl(this.data.category, Validators.required)
    });
    this.id = this.data.id;
  }

  closeModal(): void {
    this.dialogRef.close();
  }

  editItem(): void {
    if (this.itemForm.valid) {
      const item: Item = {
        name: this.itemForm.value.name,
        price: this.itemForm.value.price,
        description: this.itemForm.value.description,
        imageUrl: this.itemForm.value.imageUrl,
        category: this.itemForm.value.category,
      }
      const formData = this.itemForm.value;
      this.itemService.updateItem(this.id, item).subscribe(
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
