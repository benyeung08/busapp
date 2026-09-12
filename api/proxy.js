export default async function handler(req, res) {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET,POST,OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', '*');
  res.setHeader('Cache-Control', 's-maxage=30, stale-while-revalidate=60');
  if (req.method === 'OPTIONS') return res.status(200).end();
  const targetUrl = req.query.url;
  if (!targetUrl) return res.status(400).json({ error: '請提供 ?url=' });
  const allowedHosts = ['data.etabus.gov.hk', 'rt.data.gov.hk', 'opendata.mtr.com.hk'];
  try {
    const u = new URL(targetUrl);
    if (!allowedHosts.some(h => u.hostname.includes(h))) return res.status(403).json({ error: 'Host not allowed' });
  } catch { return res.status(400).json({ error: 'Invalid URL' }); }
  try {
    const response = await fetch(targetUrl, { headers: { 'User-Agent': 'hk-bus-app-github-proxy' } });
    const data = await response.text();
    res.setHeader('Content-Type', response.headers.get('content-type') || 'application/json');
    return res.status(response.status).send(data);
  } catch (e) { return res.status(500).json({ error: e.message }); }
}
