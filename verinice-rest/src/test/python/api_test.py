# coding=UTF8

import unittest
import requests
import json

# The following TestRunners might be used for xml-/html-reports
# but require additional non standard python modules. See
# - https://github.com/oldani/HtmlTestRunner
# - https://github.com/xmlrunner/unittest-xml-reporting
#
# import HtmlTestRunner
# import xmlrunner

class RestTest(unittest.TestCase):
    def setUp(self):
        self.url = 'http://127.0.0.1:8081'
        self.auth = ('nn', 'geheim')
        # self.auth = ('rr', 'geheim')

class ElementTest(RestTest):
    def test_get_all(self):
        self.url = self.url + '/elements'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertTrue(len(response.json()) > 10)

    def test_get_all_with_size_limit(self):
        self.url = self.url + '/elements?size=10'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(len(response.json()), 10, 'Should only obtain 10 elements')

    def test_get_by_property_value(self):
        self.url = self.url + '/elements?key=person_kuerzel&value=P1'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(len(response.json()), 2)
        self.assertEqual(response.json()[0]['properties']['nachname'][0], 'M. Müller')
        self.assertEqual(response.json()[1]['properties']['nachname'][0], 'Mustermann')

    def test_get_scope_element_by_property_value(self):
        self.url = self.url + '/scope/543/elements?key=person_kuerzel&value=P1'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(len(response.json()), 1)
        self.assertEqual(response.json()[0]['properties']['nachname'][0], 'M. Müller')

    def test_get_scope_elements(self):
        self.url = self.url + '/scope/63249/elements'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(len(response.json()), 11)

    def test_get_by_uuid(self):
        self.url = self.url + '/element/dbd36b1d-ced1-4421-9cc7-fa2708dc0581'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(response.json()['uuid'], 'dbd36b1d-ced1-4421-9cc7-fa2708dc0581')
        self.assertEqual(response.json()['dbid'], 63291)

    def test_get_by_dbid(self):
        self.url = self.url + '/element/63291'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(response.json()['uuid'], 'dbd36b1d-ced1-4421-9cc7-fa2708dc0581')
        self.assertEqual(response.json()['dbid'], 63291)

    def test_get_by_source_and_ext_id(self):
        self.url = self.url + '/elements/source-id/VRC/ext-id/ENTITY_13959583'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)

    def test_get_children(self):
        self.url = self.url + '/element/465/children'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertEqual(response.json()[0]['parentId'], 465)
        self.assertEqual(response.json()[0]['properties']['person-iso_surname'][0], 'Musterfrau')
        self.assertEqual(response.json()[0]['properties']['person-iso_name'][0], 'Maria')

    def test_create_element(self):
        self.url = self.url + '/elements'
        with open('new-element.json') as file:
            data = json.load(file)
            response = requests.post(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 201, response.text)
            self.assertTrue('Location' in response.headers, 'The location header has to be set.')
            self.assertNotEqual(response.json()['dbid'], 0, 'The response should contain the created element with dbid set.')

    def test_create_element_with_existing_uuid(self):
        self.url = self.url + '/elements'
        with open('new-element-with-existing-uuid.json') as file:
            data = json.load(file)
            response = requests.post(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 400, response.text)

    def test_update_element(self):
        self.url = self.url + '/element/63432'
        with open('updated-element.json') as file:
            data = json.load(file)
            response = requests.put(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 204, response.text)

    def test_update_element_with_existing_uuid(self):
        self.url = self.url + '/element/63432'
        with open('updated-element-with-existing-uuid.json') as file:
            data = json.load(file)
            response = requests.put(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 400, response.text)

    def test_update_element_with_invalid_uuid(self):
        self.url = self.url + '/element/63432'
        with open('updated-element-with-invalid-uuid.json') as file:
            data = json.load(file)
            response = requests.put(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 400, response.text)

    def test_update_missing_element(self):
        self.url = self.url + '/element/33'
        with open('updated-element-with-wrong-dbid.json') as file:
            data = json.load(file)
            response = requests.put(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 404, response.text)

    def test_update_invalid_dbid(self):
        self.url = self.url + '/element/0'
        with open('updated-element-with-wrong-dbid.json') as file:
            data = json.load(file)
            response = requests.put(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 400, response.text)

    def test_get_links(self):
        self.url = self.url + '/links'
        response = requests.get(self.url, auth=self.auth)
        self.assertEqual(response.status_code, 200, response.text)
        self.assertTrue(len(response.json()) >= 288)

    def test_create_link(self):
        self.url = self.url + '/links'
        with open('new-link.json') as file:
            data = json.load(file)
            response = requests.post(self.url, auth=self.auth, json=data)
            self.assertEqual(response.status_code, 201, response.text)

if __name__ == '__main__':
    unittest.main()
