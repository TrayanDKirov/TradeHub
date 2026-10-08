export interface UserPreviewDto {
  id: number;
  username: string;
  role: "buyer" | "seller" | "admin" | "";
}
