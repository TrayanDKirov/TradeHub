import { CartItemResponseDto } from "./cart-item-response.dto";

export interface CartResponseDto {
  id: number;
  items: CartItemResponseDto[];
  itemsCount: number;
  totalPrice: number;
}