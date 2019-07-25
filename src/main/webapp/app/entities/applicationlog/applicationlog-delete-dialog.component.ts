import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationlog } from 'app/shared/model/applicationlog.model';
import { ApplicationlogService } from './applicationlog.service';

@Component({
  selector: 'jhi-applicationlog-delete-dialog',
  templateUrl: './applicationlog-delete-dialog.component.html'
})
export class ApplicationlogDeleteDialogComponent {
  applicationlog: IApplicationlog;

  constructor(
    protected applicationlogService: ApplicationlogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.applicationlogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationlogListModification',
        content: 'Deleted an applicationlog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-applicationlog-delete-popup',
  template: ''
})
export class ApplicationlogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationlog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationlogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.applicationlog = applicationlog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/applicationlog', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/applicationlog', { outlets: { popup: null } }]);
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
