import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {Router} from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {ShopItemPreviewDto} from '../../../dtos/shop-item/shop-item-preview.dto';
import {environment} from '../../../../environments/environment';

@Component({
  imports: [MatCardModule, MatButtonModule, MatIconModule, CurrencyPipe],
  selector: 'app-shop-item-card',
  styleUrl: './shop-item-card.component.css',
  templateUrl: './shop-item-card.component.html',
})
export class ShopItemCardComponent {
  @Input({required: true}) item!: ShopItemPreviewDto;
  @Input() canDelete = false;
  @Output() deleteRequest = new EventEmitter<number>();

  constructor(private router: Router) { }

  get pictureSrc(): string {
    return `${environment.host}${this.item.pictureUrl}`;
  }

  viewItem() {
    this.router.navigate([`/item/${this.item.id}`]);
  }

  onDelete(event: Event) {
    event.stopPropagation();
    this.deleteRequest.emit(this.item.id);
  }
}