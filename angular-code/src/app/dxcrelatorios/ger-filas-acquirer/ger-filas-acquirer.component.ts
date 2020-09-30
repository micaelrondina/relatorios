import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { FileService } from '../../shared/services/file.service';

@Component({
    selector: 'app-ger-filas-acquirer',
    templateUrl: './ger-filas-acquirer.component.html',
    styleUrls: ['./ger-filas-acquirer.component.css']
})
export class RelGerFilasAcquirerComponent implements OnInit, OnDestroy {
    relatorios = null;
    temArquivos: boolean = false;
    private sub: Subscription[] = [];

    constructor(private fileService: FileService) { }

    ngOnInit() {
        this.sub.push(this.fileService.getRelGerFilasAcquirer().subscribe(v => {
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
        this.fileService.downloadRelGerFilasAcquirer(idRel);
    }
}
