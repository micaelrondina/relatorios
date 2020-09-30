import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RelEvent1386Component } from './rel-event-1386/rel-event-1386.component';
import { RelGerFilasEmissorComponent } from './ger-filas-emissor/ger-filas-emissor.component';
import { RelGerFilasAcquirerComponent } from './ger-filas-acquirer/ger-filas-acquirer.component';
import { HomeComponent } from '../shared/home/home.component';
import { AuthGuard } from '../shared/auth.guard';
import { SemAcessoComponent } from '../shared/sem-acesso/sem-acesso.component';
import { ExtratoECComponent } from './extrato-ec/extrato-ec.component';

const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'sem-acesso', component: SemAcessoComponent },
    { path: 'extrato-ec', component: ExtratoECComponent },
    {
        path: 'rel-eventos-1386', component: RelEvent1386Component, canActivate: [AuthGuard], data: {
            accessRole: 'dxcrel.eventos.1386' //permissao que essa rota precisa para ser acessada
        }
    },
    {
        path: 'ger-filas-emissor', component: RelGerFilasEmissorComponent, canActivate: [AuthGuard], data: {
            accessRole: 'dxcrel.ger.filas.emissor' //permissao que essa rota precisa para ser acessada
        }
    },
    {
        path: 'ger-filas-acquirer', component: RelGerFilasAcquirerComponent, canActivate: [AuthGuard], data: {
            accessRole: 'dxcrel.ger.filas.acquirer' //permissao que essa rota precisa para ser acessada
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DxcRelatoriosRoutingModule { }