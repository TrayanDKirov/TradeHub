import { MessageResponseDto } from "../message.dto";

export interface TokenResponseDto {
    message: MessageResponseDto;
    token: string;
}