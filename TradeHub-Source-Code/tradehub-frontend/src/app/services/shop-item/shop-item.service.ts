import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {ApiResponse} from '../../dtos/api-response.dto';
import {unwrap} from '../../utils/api-response.operators';
import {NotificationService} from '../success-dialog.service';
import {ShopItemCreateDto} from '../../dtos/shop-item/shop-item-create.dto';
import {ShopItemPreviewDto} from '../../dtos/shop-item/shop-item-preview.dto';
import {ShopItemResponseDto} from '../../dtos/shop-item/shop-item-response.dto';

@Injectable({
  providedIn: 'root'
})
export class ShopItemService {
  shopItemUrl: string = `${environment.backendUrl}/shop-items`

  constructor(private http: HttpClient,
    private notificationService: NotificationService) { }

  getShopItem(id: number): Observable<ShopItemResponseDto> {
    return this.http.get<ApiResponse<ShopItemResponseDto>>(`${this.shopItemUrl}/${id}`)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  createShopItem(createDto: ShopItemCreateDto, picture: File): Observable<ShopItemPreviewDto> {
    const form = new FormData();
    form.append('item', new Blob([JSON.stringify(createDto)], {type: 'application/json'}));
    form.append('picture', picture);

    return this.http.post<ApiResponse<ShopItemPreviewDto>>(this.shopItemUrl, form)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  deleteShopItem(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(`${this.shopItemUrl}/${id}`)
      .pipe(
        unwrap(this.notificationService),
        map(() => undefined)
      );
  }
}