import {Component, OnDestroy} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {Router, RouterLink} from '@angular/router';
import {ShopCreateDto} from '../../../dtos/shop/shop-create.dto';
import {ShopService} from '../../../services/shop/shop.service';

@Component({
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, FormsModule, RouterLink],
  selector: 'app-shop-create',
  styleUrl: './shop-form.component.css',
  templateUrl: './shop-form.component.html',
})
export class ShopFormComponent implements OnDestroy {
  readonly acceptedTypes = "image/jpeg,image/png,image/webp";

  createDto: ShopCreateDto = {name: ""};
  picture: File | null = null;
  picturePreview: string | null = null;

  constructor(private service: ShopService,
    private router: Router
  ) { }

  onPictureSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    this.revokePreview();
    this.picture = file;
    this.picturePreview = file ? URL.createObjectURL(file) : null;
  }

  onSubmit() {
    if (!this.picture) {
      return;
    }

    this.service.createShop(this.createDto, this.picture).subscribe(
      previewDto => {this.router.navigate([`/shop/${previewDto.id}`])}
    );
  }

  ngOnDestroy() {
    this.revokePreview();
  }

  private revokePreview() {
    if (this.picturePreview) {
      URL.revokeObjectURL(this.picturePreview);
    }
  }
}
