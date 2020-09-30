import 'hammerjs';
import { NgModule, LOCALE_ID } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../material-module';
import { CdkTableModule } from '@angular/cdk/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { RelEvent1386Component } from './rel-event-1386/rel-event-1386.component';
import { RelGerFilasEmissorComponent } from './ger-filas-emissor/ger-filas-emissor.component';
import { RelGerFilasAcquirerComponent } from './ger-filas-acquirer/ger-filas-acquirer.component';
import { FormDebugComponent } from '../shared/form-debug/form-debug.component';
import { ListaSelecaoComponent } from '../shared/lista-selecao/lista-selecao.component';
import { MessagePopupComponent } from './../shared/message-popup/message-popup.component';
import { AlertPopupComponent } from '../shared/alert-popup/alert-popup.component';
import { DxcRelatoriosRoutingModule } from './dxcrelatorios.routing.module';
import { HomeComponent } from '../shared/home/home.component';
import { SemAcessoComponent } from '../shared/sem-acesso/sem-acesso.component';

import { FiltroConsultaComponent } from './filtro-consulta/filtro-consulta.component'
import { ExtratoECComponent } from './extrato-ec/extrato-ec.component';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt);

@NgModule({
  imports: [
    CommonModule,
    DxcRelatoriosRoutingModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule
  ],
  providers: [{
    provide: LOCALE_ID,
    useValue: 'pt-BR'
  }
  ],
  entryComponents: [MessagePopupComponent, AlertPopupComponent],
  declarations: [
    RelEvent1386Component,
    FormDebugComponent,
    ListaSelecaoComponent,
    MessagePopupComponent,
    AlertPopupComponent,
    HomeComponent,
    SemAcessoComponent,
    RelGerFilasEmissorComponent,
    RelGerFilasAcquirerComponent,
    ExtratoECComponent,
    FiltroConsultaComponent
  ]
})
export class DxcRelatoriosModule { }
