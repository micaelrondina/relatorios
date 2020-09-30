import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { FileService } from '../../shared/services/file.service';

@Component({
    selector: 'app-ger-filas-emissor',
    templateUrl: './ger-filas-emissor.component.html',
    styleUrls: ['./ger-filas-emissor.component.css']
})
export class RelGerFilasEmissorComponent implements OnInit, OnDestroy {
    relatorios = null;
    temArquivos: boolean = false;
    private sub: Subscription[] = [];

    constructor(private fileService: FileService) { }

    ngOnInit() {
        this.sub.push(this.fileService.getRelGerFilasEmissor().subscribe(v => {
            if (v != null) {
                this.relatorios = v;
                this.temArquivos = true;
                if (this.relatorios.length == 0){
                    this.temArquivos = false;
                }
            } else {
                this.relatorios = null;
                this.temArquivos = false;
            }
        }));
    }

    ngOnDestroy() {
        this.sub.forEach(s => s.unsubscribe());
    }

    downloadFile(idRel: number) {
        this.fileService.downloadRelGerFilasEmissor(idRel);
    }
}
