SELECT
  cte.scopeId
FROM CnaTreeElement cte
WHERE cte.dbid IN (
  SELECT
    c.personId
  FROM Configuration c
  WHERE c.entityId IN (
    SELECT
      e.dbid
    FROM Entity e
    WHERE e.dbid IN (
      SELECT
        pl.typedlistId
      FROM PropertyList pl
      WHERE pl.dbid IN (
        SELECT
          p.propertiesId
        FROM Property p
        WHERE p.propertytype = 'configuration_benutzername'
        AND p.propertyvalue = :loginName
      )
    )
  )
)