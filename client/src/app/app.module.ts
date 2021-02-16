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
import {HomeComponent} from './site/home/home.component';
import {FornecedoresComponent} from './comprador/fornecedores/fornecedores.component';
import {CompradorViewComponent} from './comprador/comprador-view/comprador-view.component';
import {RepresentanteViewComponent} from './representante/representante-view/representante-view.component';
import {RespostaCotacaoComponent} from './comprador/resposta-cotacao/resposta-cotacao.component';
import {PedidoCotacaoComponent} from './comprador/pedido-cotacao/pedido-cotacao.component';
import {RespostaRepresentanteComponent} from './representante/resposta-representante/resposta-representante.component';
import {PedidoRepresentanteComponent} from './representante/pedido-representante/pedido-representante.component';
import {CaixaDeEntradaRepresentanteComponent} from './representante/caixa-de-entrada-representante/caixa-de-entrada-representante.component';
import {ProfileComponent} from './profile/profile.component';
import {RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {UsersComponent} from './admin/users/users.component';
import {NovaCotacaoComponent} from './comprador/nova-cotacao/nova-cotacao.component';
import {AnaliseCotacaoComponent} from './comprador/analise-cotacao/analise-cotacao.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AuthService} from './auth.service';
import {AuthGuardService} from './auth-guard.service';
import {UserService} from './user.service';
import {AdminAuthGuardService} from './admin-auth-guard.service';
import {IsUserAdminPipe} from './pipe/is-user-admin.pipe';
import {ProductFormComponent} from './comprador/product-form/product-form.component';
import {ProductService} from './product.service';
import {FormsModule} from '@angular/forms';
import {CustomFormsModule} from 'ng2-validation';

@NgModule({
  declarations: [
    IsUserAdminPipe,
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
    AnaliseCotacaoComponent,
    ProductFormComponent
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
        {
          path: 'profile',
          component: ProfileComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/caixa-de-entrada-comprador',
          component: CaixaDeEntradaComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/nova-cotacao',
          component: NovaCotacaoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/analise-cotacao',
          component: AnaliseCotacaoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/resposta',
          component: RespostaCotacaoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/pedido',
          component: PedidoCotacaoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/fornecedores',
          component: FornecedoresComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/product/new',
          component: ProductFormComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/product/:id',
          component: ProductFormComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/catalogo',
          component: CatalogoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'comp/product',
          component: CatalogoComponent,
          canActivate: [AuthGuardService]
        },
        {
          path: 'rep/caixa-de-entrada-representante',
          component: CaixaDeEntradaRepresentanteComponent
        },
        {
          path: 'rep/resposta',
          component: RespostaRepresentanteComponent
        },
        {
          path: 'rep/pedido',
          component: PedidoRepresentanteComponent
        },
        {
          path: 'login',
          component: LoginComponent
        },
        {
          path: 'admin/users',
          component: UsersComponent,
          canActivate: [AdminAuthGuardService]
        },
      ],
      {useHash: false, enableTracing: false}),
    NgbModule,
    FormsModule,
    CustomFormsModule
  ],
  providers: [
    AuthService,
    AuthGuardService,
    UserService,
    AdminAuthGuardService,
    ProductService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
