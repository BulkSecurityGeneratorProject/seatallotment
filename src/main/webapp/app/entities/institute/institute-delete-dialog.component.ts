import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstitute } from 'app/shared/model/institute.model';
import { InstituteService } from './institute.service';

@Component({
  selector: 'jhi-institute-delete-dialog',
  templateUrl: './institute-delete-dialog.component.html'
})
export class InstituteDeleteDialogComponent {
  institute: IInstitute;

  constructor(protected instituteService: InstituteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.instituteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'instituteListModification',
        content: 'Deleted an institute'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-institute-delete-popup',
  template: ''
})
export class InstituteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ institute }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InstituteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.institute = institute;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/institute', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/institute', { outlets: { popup: null } }]);
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
