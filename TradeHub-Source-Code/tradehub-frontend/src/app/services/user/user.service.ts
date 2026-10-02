import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../../dtos/api-response.dto';
import {BuyerResponseDto} from '../../dtos/user/buyer/buyer-response.dto';
import {SellerResponseDto} from '../../dtos/user/seller/seller-response.dto';
import {UserPreviewDto} from '../../dtos/user/user-preview.dto';
import {unwrap} from '../../utils/api-response.operators';
import {NotificationService} from '../success-dialog.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userUrl: string = `${environment.backendUrl}/user`

  constructor(private http: HttpClient,
    private notificationService: NotificationService) { }

  private withoutEmptyFields<T extends object>(dto: T): Partial<T> {
    return Object.fromEntries(
      Object.entries(dto).filter(([, value]) => value !== "")
    ) as Partial<T>;
  }

  getUserPreview() : Observable<UserPreviewDto> {
    return this.http.get<ApiResponse<UserPreviewDto>>(`${this.userUrl}/preview`)
      .pipe(
        unwrap(this.notificationService),
      );
  }

  getBuyer() : Observable<BuyerResponseDto> {
    return this.http.get<ApiResponse<BuyerResponseDto>>(`${this.userUrl}/buyer`).pipe(
      unwrap(this.notificationService)
    );
  }

  getSeller() : Observable<SellerResponseDto> {
    return this.http.get<ApiResponse<SellerResponseDto>>(`${this.userUrl}/seller`).pipe(
      unwrap(this.notificationService)
    );
  }
}
