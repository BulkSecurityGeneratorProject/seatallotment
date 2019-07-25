import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  ApplicationlogComponent,
  ApplicationlogDetailComponent,
  ApplicationlogUpdateComponent,
  ApplicationlogDeletePopupComponent,
  ApplicationlogDeleteDialogComponent,
  applicationlogRoute,
  applicationlogPopupRoute
} from './';

const ENTITY_STATES = [...applicationlogRoute, ...applicationlogPopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationlogComponent,
    ApplicationlogDetailComponent,
    ApplicationlogUpdateComponent,
    ApplicationlogDeleteDialogComponent,
    ApplicationlogDeletePopupComponent
  ],
  entryComponents: [
    ApplicationlogComponent,
    ApplicationlogUpdateComponent,
    ApplicationlogDeleteDialogComponent,
    ApplicationlogDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentApplicationlogModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
