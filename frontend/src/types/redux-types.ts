export interface IAuthState {
    authenticated: boolean,
    jwt?: string
}

// login action just passes authenticated user's username
export interface IAction {
    type: "logout" | "login" | undefined,
    jwt?: string
}
