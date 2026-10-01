import { MessageResponseDto } from "./message.dto";

export interface ApiResponse<T> {
    message: MessageResponseDto;
    data: T;
}