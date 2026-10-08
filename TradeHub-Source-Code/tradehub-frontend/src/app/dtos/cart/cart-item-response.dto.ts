export interface CartItemResponseDto {
  id: number;
  itemId: number;
  itemName: string;
  pictureUrl: string;
  price: number;
  quantity: number;
  inStock: number;
  totalPrice: number;
  shopId: number;
  shopName: string;
}