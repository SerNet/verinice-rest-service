import unittest
import requests

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
        self.headers = { 'Authorization': 'Basic bm46Z2VoZWlt' } # nn
        # self.headers = { 'Authorization': 'Basic cnI6Z2VoZWlt' } # rr

class ElementTest(RestTest):
    def test_get_all(self):
        self.url = self.url + '/elements'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertTrue(len(response.json()) > 10)

    def test_get_all_with_size_limit(self):
        self.url = self.url + '/elements?size=10'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()), 10, 'Should only obtain 10 elements')

    def test_get_by_property_value(self):
        self.url = self.url + '/elements?key=person_kuerzel&value=P1'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()), 2)
        self.assertEqual(response.json()[0]['properties']['nachname'][0], 'M. Müller')
        self.assertEqual(response.json()[1]['properties']['nachname'][0], 'Mustermann')

    def test_get_scope_element_by_property_value(self):
        self.url = self.url + '/scope/543/elements?key=person_kuerzel&value=P1'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()), 1)
        self.assertEqual(response.json()[0]['properties']['nachname'][0], 'M. Müller')

    def test_get_scope_elements(self):
        self.url = self.url + '/scope/63249/elements'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()), 11)

    def test_get_by_uuid(self):
        self.url = self.url + '/element/dbd36b1d-ced1-4421-9cc7-fa2708dc0581'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['uuid'], 'dbd36b1d-ced1-4421-9cc7-fa2708dc0581')

    def test_get_by_dbid(self):
        self.url = self.url + '/element/63291'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json()['uuid'], 'dbd36b1d-ced1-4421-9cc7-fa2708dc0581')

    def test_get_by_source_and_ext_id(self):
        self.url = self.url + '/elements/source-id/VRC/ext-id/ENTITY_13959583'
        response = requests.get(self.url, headers=self.headers)
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
