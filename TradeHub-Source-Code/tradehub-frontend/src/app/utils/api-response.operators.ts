import { OperatorFunction, map, tap } from 'rxjs';
import {NotificationService} from '../services/success-dialog.service';
import {ApiResponse} from '../dtos/api-response.dto';

export function unwrap<T>(notification: NotificationService): OperatorFunction<ApiResponse<T>, T> {
  return source => source.pipe(
    tap(response => notification.showSuccess(response.message)),
    map(response => response.data)
  );
}
