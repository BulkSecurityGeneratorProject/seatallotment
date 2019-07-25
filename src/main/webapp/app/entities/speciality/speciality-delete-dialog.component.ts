import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeciality } from 'app/shared/model/speciality.model';
import { SpecialityService } from './speciality.service';

@Component({
  selector: 'jhi-speciality-delete-dialog',
  templateUrl: './speciality-delete-dialog.component.html'
})
export class SpecialityDeleteDialogComponent {
  speciality: ISpeciality;

  constructor(
    protected specialityService: SpecialityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.specialityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'specialityListModification',
        content: 'Deleted an speciality'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-speciality-delete-popup',
  template: ''
})
export class SpecialityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ speciality }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpecialityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.speciality = speciality;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/speciality', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/speciality', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
