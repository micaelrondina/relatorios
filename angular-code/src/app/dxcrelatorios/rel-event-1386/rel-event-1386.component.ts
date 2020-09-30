import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { FileService } from '../../shared/services/file.service';

@Component({
    selector: 'app-rel-event-1386',
    templateUrl: './rel-event-1386.component.html',
    styleUrls: ['./rel-event-1386.component.css']
})
export class RelEvent1386Component implements OnInit, OnDestroy {
    relatorios = null;
    temArquivos: boolean = false;
    private sub: Subscription[] = [];

    constructor(private fileService: FileService) { }

    ngOnInit() {
        this.sub.push(this.fileService.getRelEvent1386().subscribe(v => {
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
        this.fileService.downloadRelEvent1386(idRel);
    }
}
