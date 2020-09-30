
export interface ApiRequest<T> {
    filtro: T;
    dados: T;
}

export interface ApiResponse<T> {
    dados: T;
    falha: ApiError;
}

export interface ApiError {
    message: string;
    erros: string[];
}

export interface FiltroConsultar {
    acquirerId: number;
    dataInicio: string;
    dataFim: string;
    rubrica: number;
    comercio: number;
    produto: number;
    filial: number;
}
