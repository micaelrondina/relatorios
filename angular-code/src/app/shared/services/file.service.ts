import { take } from 'rxjs/operators';


import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { File } from '../models/file';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};
@Injectable({
    providedIn: 'root'
})
export class FileService {

    private readonly API = `${environment.API}`;

    constructor(private http: HttpClient) { }

    //Relatorio Eventos 1386
    getRelEvent1386(): Observable<File> {
        return this.http.get<File>(`${this.API}rel-event-1386/listar`, httpOptions).pipe(take(1));
    }

    downloadRelEvent1386(idRel: number) {
        window.open(`${this.API}rel-event-1386/download/${idRel}`, "_blank");
    }


    //Relatório Gerencial Filas Acquirer
    getRelGerFilasAcquirer(): Observable<File> {
        return this.http.get<File>(`${this.API}rel-ger-filas-acquirer/listar`, httpOptions).pipe(take(1));
    }

    downloadRelGerFilasAcquirer(idRel: number) {
        window.open(`${this.API}rel-ger-filas-acquirer/download/${idRel}`, "_blank");
    }

    
    //Relatório Gerencial Filas Emissor
    getRelGerFilasEmissor(): Observable<File> {
        return this.http.get<File>(`${this.API}rel-ger-filas-emissor/listar`, httpOptions).pipe(take(1));
    }

    downloadRelGerFilasEmissor(idRel: number) {
        window.open(`${this.API}rel-ger-filas-emissor/download/${idRel}`, "_blank");
    }
}
