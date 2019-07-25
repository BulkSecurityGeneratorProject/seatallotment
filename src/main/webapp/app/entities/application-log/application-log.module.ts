import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  ApplicationLogComponent,
  ApplicationLogDetailComponent,
  ApplicationLogUpdateComponent,
  ApplicationLogDeletePopupComponent,
  ApplicationLogDeleteDialogComponent,
  applicationLogRoute,
  applicationLogPopupRoute
} from './';

const ENTITY_STATES = [...applicationLogRoute, ...applicationLogPopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationLogComponent,
    ApplicationLogDetailComponent,
    ApplicationLogUpdateComponent,
    ApplicationLogDeleteDialogComponent,
    ApplicationLogDeletePopupComponent
  ],
  entryComponents: [
    ApplicationLogComponent,
    ApplicationLogUpdateComponent,
    ApplicationLogDeleteDialogComponent,
    ApplicationLogDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentApplicationLogModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
