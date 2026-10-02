export type Role = "BUYER" | "SELLER" | "ADMIN";

export interface UserResponseDto {
    id: number;
    username: string;
    email: string;
    phone: string;
    createdAt: string;
    role: Role;
}
