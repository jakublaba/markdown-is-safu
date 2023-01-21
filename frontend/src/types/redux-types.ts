export interface IAuthState {
    authenticated: boolean,
    jwt: string //empty string if not authenticated
}

// login action just passes authenticated user's jwt
// blank string passed on logout
export interface IAction {
    type: "logout" | "login" | undefined,
    jwt: string
}
