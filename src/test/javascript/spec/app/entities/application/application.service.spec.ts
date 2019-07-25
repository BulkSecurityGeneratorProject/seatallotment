/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ApplicationService } from 'app/entities/application/application.service';
import { IApplication, Application } from 'app/shared/model/application.model';

describe('Service Tests', () => {
  describe('Application Service', () => {
    let injector: TestBed;
    let service: ApplicationService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplication;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ApplicationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Application(
        'ID',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            applicant_dob: currentDate.format(DATE_TIME_FORMAT),
            entry_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('9fec3727-3421-4967-b213-ba36557ca194')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Application', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            applicant_dob: currentDate.format(DATE_TIME_FORMAT),
            entry_date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            applicant_dob: currentDate,
            entry_date: currentDate
          },
          returnedFromService
        );
        service
          .create(new Application(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Application', async () => {
        const returnedFromService = Object.assign(
          {
            application_no: 'BBBBBB',
            applicant_name: 'BBBBBB',
            applicant_email: 'BBBBBB',
            applicant_mobile: 'BBBBBB',
            applicant_dob: currentDate.format(DATE_TIME_FORMAT),
            applicant_address: 'BBBBBB',
            course: 'BBBBBB',
            institute: 'BBBBBB',
            speciality: 'BBBBBB',
            course_doc: 'BBBBBB',
            first_preference: 'BBBBBB',
            second_preference: 'BBBBBB',
            third_preference: 'BBBBBB',
            entryby: 'BBBBBB',
            entry_date: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            applicant_dob: currentDate,
            entry_date: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Application', async () => {
        const returnedFromService = Object.assign(
          {
            application_no: 'BBBBBB',
            applicant_name: 'BBBBBB',
            applicant_email: 'BBBBBB',
            applicant_mobile: 'BBBBBB',
            applicant_dob: currentDate.format(DATE_TIME_FORMAT),
            applicant_address: 'BBBBBB',
            course: 'BBBBBB',
            institute: 'BBBBBB',
            speciality: 'BBBBBB',
            course_doc: 'BBBBBB',
            first_preference: 'BBBBBB',
            second_preference: 'BBBBBB',
            third_preference: 'BBBBBB',
            entryby: 'BBBBBB',
            entry_date: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            applicant_dob: currentDate,
            entry_date: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Application', async () => {
        const rxPromise = service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
