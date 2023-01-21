export const AUTH_API_URL = "http://localhost:8080/api/auth";

export interface IRegisterRequest {
    username: string,
    email: string,
    password: string
}

export interface ILoginRequest {
    username: string,
    password: string
}

export interface ILoginResponse {
    username: string,
    email: string,
    jwt: string
}