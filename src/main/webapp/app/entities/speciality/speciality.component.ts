import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISpeciality } from 'app/shared/model/speciality.model';
import { AccountService } from 'app/core';
import { SpecialityService } from './speciality.service';

@Component({
  selector: 'jhi-speciality',
  templateUrl: './speciality.component.html'
})
export class SpecialityComponent implements OnInit, OnDestroy {
  specialities: ISpeciality[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected specialityService: SpecialityService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.specialityService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpeciality[]>) => res.ok),
        map((res: HttpResponse<ISpeciality[]>) => res.body)
      )
      .subscribe(
        (res: ISpeciality[]) => {
          this.specialities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSpecialities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpeciality) {
    return item.id;
  }

  registerChangeInSpecialities() {
    this.eventSubscriber = this.eventManager.subscribe('specialityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
