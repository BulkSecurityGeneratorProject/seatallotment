/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeatAllotmentTestModule } from '../../../test.module';
import { SpecialityDeleteDialogComponent } from 'app/entities/speciality/speciality-delete-dialog.component';
import { SpecialityService } from 'app/entities/speciality/speciality.service';

describe('Component Tests', () => {
  describe('Speciality Management Delete Component', () => {
    let comp: SpecialityDeleteDialogComponent;
    let fixture: ComponentFixture<SpecialityDeleteDialogComponent>;
    let service: SpecialityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [SpecialityDeleteDialogComponent]
      })
        .overrideTemplate(SpecialityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
