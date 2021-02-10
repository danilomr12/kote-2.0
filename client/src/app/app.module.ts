import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CatalogoComponent} from './comprador/catalogo/catalogo.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {AppAngularMaterialModule} from './app-angular-material-module';
import {CaixaDeEntradaComponent} from './comprador/caixa-de-entrada/caixa-de-entrada.component';
import {AngularFireModule} from '@angular/fire';
import {AngularFireDatabaseModule} from '@angular/fire/database';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {environment} from '../environments/environment';
import {BsNavBarComponent} from './bs-nav-bar/bs-nav-bar.component';
import {HomeComponent} from './home/home.component';
import {FornecedoresComponent} from './comprador/fornecedores/fornecedores.component';
import {CompradorViewComponent} from './comprador/comprador-view/comprador-view.component';
import {RepresentanteViewComponent} from './representante-view/representante-view.component';
import {RespostaCotacaoComponent} from './comprador/resposta-cotacao/resposta-cotacao.component';
import {PedidoCotacaoComponent} from './comprador/pedido-cotacao/pedido-cotacao.component';
import {RespostaRepresentanteComponent} from './resposta-representante/resposta-representante.component';
import {PedidoRepresentanteComponent} from './pedido-representante/pedido-representante.component';
import {CaixaDeEntradaRepresentanteComponent} from './caixa-de-entrada-representante/caixa-de-entrada-representante.component';
import {ProfileComponent} from './profile/profile.component';
import {RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {UsersComponent} from './admin/users/users.component';
import {NovaCotacaoComponent} from './comprador/nova-cotacao/nova-cotacao.component';
import {AnaliseCotacaoComponent} from './comprador/analise-cotacao/analise-cotacao.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    CatalogoComponent,
    CaixaDeEntradaComponent,
    BsNavBarComponent,
    HomeComponent,
    FornecedoresComponent,
    CompradorViewComponent,
    RepresentanteViewComponent,
    RespostaCotacaoComponent,
    PedidoCotacaoComponent,
    RespostaRepresentanteComponent,
    PedidoRepresentanteComponent,
    CaixaDeEntradaRepresentanteComponent,
    ProfileComponent,
    LoginComponent,
    UsersComponent,
    NovaCotacaoComponent,
    AnaliseCotacaoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    AppAngularMaterialModule,
    NgbModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'comp/caixa-de-entrada-comprador', component: CaixaDeEntradaComponent},
      {path: 'comp/nova-cotacao', component: NovaCotacaoComponent},
      {path: 'comp/analise-cotacao', component: AnaliseCotacaoComponent},
      {path: 'comp/resposta', component: RespostaCotacaoComponent},
      {path: 'comp/pedido', component: PedidoCotacaoComponent},
      {path: 'comp/fornecedores', component: FornecedoresComponent},
      {path: 'comp/catalogo', component: CatalogoComponent},
      {path: 'rep/caixa-de-entrada-representante', component: CaixaDeEntradaRepresentanteComponent},
      {path: 'rep/resposta', component: RespostaRepresentanteComponent},
      {path: 'rep/pedido', component: PedidoRepresentanteComponent},
      {path: 'login', component: LoginComponent},
      {path: 'admin/users', component: UsersComponent}
    ]),
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
