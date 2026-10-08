import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {ApiResponse} from '../../dtos/api-response.dto';
import {unwrap} from '../../utils/api-response.operators';
import {NotificationService} from '../success-dialog.service';
import {CartResponseDto} from '../../dtos/cart/cart-response.dto';
import {CartItemAddDto} from '../../dtos/cart/cart-item-add.dto';
import {CartItemUpdateDto} from '../../dtos/cart/cart-item-update.dto';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartUrl: string = `${environment.backendUrl}/cart`

  constructor(private http: HttpClient,
    private notificationService: NotificationService) { }

  getCart(): Observable<CartResponseDto> {
    return this.http.get<ApiResponse<CartResponseDto>>(this.cartUrl)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  addItem(addDto: CartItemAddDto): Observable<CartResponseDto> {
    return this.http.post<ApiResponse<CartResponseDto>>(`${this.cartUrl}/items`, addDto)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  updateItem(cartItemId: number, updateDto: CartItemUpdateDto): Observable<CartResponseDto> {
    return this.http.patch<ApiResponse<CartResponseDto>>(`${this.cartUrl}/items/${cartItemId}`, updateDto)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  removeItem(cartItemId: number): Observable<CartResponseDto> {
    return this.http.delete<ApiResponse<CartResponseDto>>(`${this.cartUrl}/items/${cartItemId}`)
      .pipe(
        unwrap(this.notificationService)
      );
  }

  clearCart(): Observable<CartResponseDto> {
    return this.http.delete<ApiResponse<CartResponseDto>>(this.cartUrl)
      .pipe(
        unwrap(this.notificationService)
      );
  }
}