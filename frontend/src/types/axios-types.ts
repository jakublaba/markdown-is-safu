export const AUTH_API_URL = "http://localhost:8080/api/auth";

export const NOTE_API_URL = "http://localhost:8080/api/notes";

export interface IRegisterRequest {
    username: string,
    email: string,
    password: string
}

export interface ILoginResponse {
    username: string,
    email: string,
    jwt: string
}

export interface IUser {
    username: string,
    email: string
}

export interface INote {
    uuid: string,
    name: string,
    owner: IUser
}
