import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {ApiResponse} from '../../dtos/api-response.dto';
import {unwrap} from '../../utils/api-response.operators';
import {NotificationService} from '../success-dialog.service';
import {ShopCreateDto} from '../../dtos/shop/shop-create.dto';
import {ShopPreviewDto} from '../../dtos/shop/shop-preview.dto';
import {ShopResponseDto} from '../../dtos/shop/shop-response.dto';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  shopUrl: string = `${environment.backendUrl}/shops`

  constructor(private http: HttpClient,
    private notificationService: NotificationService) { }

  getShops(userId?: number): Observable<ShopPreviewDto[]> {
    let params = new HttpParams();
    if (userId !== undefined) {
      params = params.set('userId', userId);
    }

    return this.http.get<ApiResponse<ShopPreviewDto[]>>(this.shopUrl, {params})
      .pipe(
        unwrap(this.notificationService)
      );
  }

  getMyShops(): Observable<ShopPreviewDto[]> {
    return this.http.get<ApiResponse<ShopPreviewDto[]>>(`${this.shopUrl}/my`)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  getShop(id: number): Observable<ShopResponseDto> {
    return this.http.get<ApiResponse<ShopResponseDto>>(`${this.shopUrl}/${id}`)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  createShop(createDto: ShopCreateDto, picture: File): Observable<ShopPreviewDto> {
    const form = new FormData();
    form.append('shop', new Blob([JSON.stringify(createDto)], {type: 'application/json'}));
    form.append('picture', picture);

    return this.http.post<ApiResponse<ShopPreviewDto>>(this.shopUrl, form)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  deleteShop(id: number): Observable<null> {
    return this.http.delete<ApiResponse<null>>(`${this.shopUrl}/${id}`)
      .pipe(
        unwrap(this.notificationService)
      );
  }
}
