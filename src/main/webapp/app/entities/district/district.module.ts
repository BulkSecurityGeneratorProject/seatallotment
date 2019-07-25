import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SeatAllotmentSharedModule } from 'app/shared';
import {
  DistrictComponent,
  DistrictDetailComponent,
  DistrictUpdateComponent,
  DistrictDeletePopupComponent,
  DistrictDeleteDialogComponent,
  districtRoute,
  districtPopupRoute
} from './';

const ENTITY_STATES = [...districtRoute, ...districtPopupRoute];

@NgModule({
  imports: [SeatAllotmentSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DistrictComponent,
    DistrictDetailComponent,
    DistrictUpdateComponent,
    DistrictDeleteDialogComponent,
    DistrictDeletePopupComponent
  ],
  entryComponents: [DistrictComponent, DistrictUpdateComponent, DistrictDeleteDialogComponent, DistrictDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeatAllotmentDistrictModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
