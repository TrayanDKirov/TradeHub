import {Component, OnDestroy} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {ShopItemCreateDto} from '../../../dtos/shop-item/shop-item-create.dto';
import {ShopItemService} from '../../../services/shop-item/shop-item.service';

@Component({
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, FormsModule, RouterLink],
  selector: 'app-shop-item-create',
  styleUrl: './shop-item-form.component.css',
  templateUrl: './shop-item-form.component.html',
})
export class ShopItemFormComponent implements OnDestroy {
  readonly acceptedTypes = "image/jpeg,image/png,image/webp";

  createDto: ShopItemCreateDto = {name: "", price: 0, quantity: 1, description: "", shopId: 0};
  picture: File | null = null;
  picturePreview: string | null = null;

  constructor(private service: ShopItemService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.createDto.shopId = Number(this.route.snapshot.paramMap.get('shopId'));
  }

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

    this.service.createShopItem(this.createDto, this.picture).subscribe(
      previewDto => {this.router.navigate([`/item/${previewDto.id}`])}
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