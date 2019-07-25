import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  InstituteComponent,
  InstituteDetailComponent,
  InstituteUpdateComponent,
  InstituteDeletePopupComponent,
  InstituteDeleteDialogComponent,
  instituteRoute,
  institutePopupRoute
} from './';

const ENTITY_STATES = [...instituteRoute, ...institutePopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InstituteComponent,
    InstituteDetailComponent,
    InstituteUpdateComponent,
    InstituteDeleteDialogComponent,
    InstituteDeletePopupComponent
  ],
  entryComponents: [InstituteComponent, InstituteUpdateComponent, InstituteDeleteDialogComponent, InstituteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentInstituteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
